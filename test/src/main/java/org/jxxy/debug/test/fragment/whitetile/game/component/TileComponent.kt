package cn.lentme.projects.whitetile.game.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import cn.lentme.projects.whitetile.core.*
import cn.lentme.projects.whitetile.core.base.BaseComponent
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class TileComponent: BaseComponent() {
    private val tag = "TileComponent"

    private data class Tile(var position: Int, var size: Int, var black: Int, var state: Int = 0)

    private val _layerSize = 4

    private var tiles = LinkedList<Tile>()
    private val height = GameFrameHeight / _layerSize.toFloat()

    private var _gameState = 0 // 0是准备 1是开始 2是游戏结束
    private var _counter = 0

    private var _speed = InitSpeed
    private var _offset = 0f
    private var _offsetFlag = false
    private var _rollBackUp = true

    private val paint = Paint()

    private var _touchX: Float = 0f
    private var _touchY: Float = 0f
    private var _errTouchPos: Tile? = null
    private var _onTouch = false
    private var _onMove = false
    private var _lastTouchTime = 0L

    private var _tempLastTouchTime = 0L

    private val _speedTextSize = BaseTextSize * 2

    private val _random = Random(System.currentTimeMillis())

    private fun initTiles() {
        for( i in _layerSize - 1 downTo -1) {
            tiles.offer(Tile(i, 4, _random.nextInt(4)))
        }
    }

    init {
        paint.isAntiAlias = true
        paint.textSize = BaseTextSize * 2

        initTiles()
    }

    private fun isTouch(tx: Float, ty: Float, it: Tile, ignoreLayer: Boolean = true, isDownLayerRestrict: Boolean = false): Boolean {
        val width = GameFrameWidth / it.size.toFloat()
        if ( ignoreLayer && ty > it.position * height + _offset && ty < (it.position + 1) * height + _offset ) {
            if( tx > it.black * width && tx < (it.black + 1) * width ) {
                return true
            }
        }

        if( isDownLayerRestrict && (ty > it.position * height + _offset)) {
            if( tx > it.black * width && tx < (it.black + 1) * width ) {
                return true
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?, scX: Float, scY: Float): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if(!_onTouch) {
                    _onTouch = true
                    _touchX = event.getX(0) * scX
                    _touchY = event.getY(0) * scY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                _touchX = event.getX(0) * scX
                _touchY = event.getY(0) * scY
                _onMove = true
                _onTouch = true
            }
            MotionEvent.ACTION_UP -> {
                _onMove = false
            }
        }
        return true
    }

    private fun addScore(tile: Tile) {
        tile.state = 1
        if(_counter++ == 0) {
            _gameState = 1
        }
        if(_counter % SpeedUpCounter == 0)
            _speed += SpeedUpUnit
    }

    private fun gameOver(it: Tile) {
        _errTouchPos = Tile(it.position, it.size, it.black)
        _gameState = 2
        Log.d(tag, "Game Over!!!")
    }

    private fun touchTileEvent() {
        if(!_onTouch)
            return
        val endTouchTime = System.currentTimeMillis()
        if(endTouchTime - _lastTouchTime > 100) {

            // 判断是否点击到了目标
            val iterator = tiles.iterator()
            var clickedSavedTile = false
            while(iterator.hasNext()) {
                val it = iterator.next()
                if(it.state == 1) {
                    if(isTouch(_touchX, _touchY, it, ignoreLayer = false, isDownLayerRestrict = true))
                        clickedSavedTile = true
                }
                if(it.state == 0) {
                    if(isTouch(_touchX, _touchY, it)) {
                        addScore(it)
                    }
                    else if(!clickedSavedTile && !_onMove) {
                        if(it.position == -1)
                            _rollBackUp = false
                        gameOver(it)
                    }
                    break
                }

            }

            _lastTouchTime = endTouchTime
        }
        _onTouch = false
    }

    private fun touchEventUpdate() {
        when(_gameState) {
            0, 1 -> touchTileEvent()
            2 -> {
                if(_onTouch) {
                    if(_offsetFlag) {
                        if(_tempLastTouchTime == 0L || System.currentTimeMillis() - _tempLastTouchTime > 700) {
                            _tempLastTouchTime = System.currentTimeMillis()
                        }
                        else {
                            _gameState = 3
                        }
                    }
                    _onTouch = false
                }
            }
        }
    }

    override fun update(deltaTime: Float): Boolean {
        touchEventUpdate()

        when(_gameState) {
            1 -> {
                _offset += _speed * deltaTime
                if (_offset >= height) {
                    val iterator = tiles.iterator()
                    while (iterator.hasNext() && _gameState == 1) {
                        val it = iterator.next()
                        if(it.position == _layerSize - 1 && it.state == 0) {
                            gameOver(it)
                            return true
                        }
                        it.position++
                        if (it.position == _layerSize)
                            iterator.remove()
                    }
                    tiles.offer(Tile(-1, 4, _random.nextInt(4)))
                    _offset -= height
                }
            }
            2 -> {
                if(_rollBackUp) {
                    if(_offset <= _speed * deltaTime) {
                        _offset = 0f
                        _offsetFlag = true
                    }
                    if(_offset > 0) {
                        _offset -= _speed * deltaTime
                    }
                }
                else {
                    if(_offset >= height - _speed * deltaTime) {
                        _offset = height
                        _offsetFlag = true
                    }
                    if(_offset < height) {
                        _offset += _speed * deltaTime
                    }
                }
            }
            3 -> {
                _speed = InitSpeed
                _offsetFlag = false
                _rollBackUp = true
                _offset = 0f
                _counter = 0
                tiles.clear()
                initTiles()
                _gameState = 0
            }
        }

        return true
    }

    override fun draw(canvas: Canvas) {
        val it = tiles.iterator()
        while(it.hasNext()) {
            val tile = it.next()
            val width = GameFrameWidth / tile.size.toFloat()
            for( i in 0..tile.size ) {
                paint.color =
                    if (i == tile.black)
                        if ( tile.state == 0 ) Color.BLACK else Color.GRAY
                    else Color.WHITE
                canvas.drawRect(i * width + 1, tile.position * height + _offset + 1,
                    (i + 1) * width - 1, (tile.position + 1) * height + _offset - 1, paint)
            }
        }

        if(_gameState == 2) {
            _errTouchPos?.let {
                val width = GameFrameWidth / it.size
                paint.color = Color.RED
                canvas.drawRect(it.black * width + 1f, it.position * height + _offset + 1,
                    (it.black + 1) * width - 1f, (it.position + 1) * height + _offset - 1, paint)
            }
        }

        paint.color = Color.RED
        val nf = NumberFormat.getNumberInstance()
        nf.maximumFractionDigits = 3
        nf.minimumFractionDigits = 3
        nf.roundingMode = RoundingMode.HALF_UP
        val title = "speed: ${nf.format(_speed)}"
        canvas.drawText(title, GameFrameWidth / 2.0f - _speedTextSize * title.length / 4, _speedTextSize * 2, paint)
    }
}