import time

import tensorflow as tf
import numpy as np
# from PyQt5 import QtCore, QtGui
# from PyQt5.QtCore import QThread
import cv2
from tensorflow.python.keras import Sequential
from tensorflow.python.keras.layers import Dense
from os.path import dirname, join

# from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
# from matplotlib.figure import Figure


class Camera_Thread_class():
    def __init__(self):
        # super().__init__()
        self.running_flag = True
        # self.ui = ui
        # 初始化keras模型
        self.model = self.get_student_model()
        # self.types = ['Angry', 'Happy', 'Neutral', 'Sad', 'Surprise']
        # self.initEmojis()
        # self.initBar()

    def get_student_model(self):
        stu_base = tf.keras.applications.MobileNetV2(
            input_shape=None,
            alpha=1.0,
            include_top=True,
            # weights='imagenet',
            input_tensor=None,
            pooling=None,
            classes=1000,
           # classifier_activation='softmax'
        )

        # 生成一个model
        student_model = Sequential(stu_base)
        student_model.add(Dense(5, activation="softmax"))
        filename = join(dirname(__file__), "weight.h5")
        student_model.load_weights(filename)

        return student_model

    # showCamera的附属函数
    def getDetectCamera(self, pics2, height,width,channels):
        pics = np.frombuffer(pics2, dtype=np.uint8).reshape((height, width, channels))
        frame = pics
        frame = cv2.cvtColor(frame, cv2.COLOR_GRAY2BGR)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        frame_raw = pics.copy()
        # OpenCV人脸识别分类器
        filename = join(dirname(__file__), "haarcascade_frontalface_default.xml")
        classifier = cv2.CascadeClassifier(
            filename
        )
        color = (255, 0, 0)
        # 调用识别人脸
        faceRects = classifier.detectMultiScale(
            gray, scaleFactor=1.2, minNeighbors=3, minSize=(150, 150))
        x, y, w, h = (0, 0, 0, 0)
        if len(faceRects):  # 大于0则检测到人脸
            for faceRect in faceRects:  # 单独框出每一张人脸
                x, y, w, h = faceRect
                cv2.rectangle(frame, (x, y), (x + h, y + w), color, 5)
                cv2.circle(frame, (x + w // 4, y + h // 4 + 30), min(w // 8, h // 8),
                           color, 5)
                cv2.circle(frame, (x + 3 * w // 4, y + h // 4 + 30), min(w // 8, h // 8),
                           color, 5)

                cv2.rectangle(frame, (x + 3 * w // 8, y + 3 * h // 4),
                              (x + 5 * w // 8, y + 7 * h // 8), color, 5)
        if len(faceRects) > 0:
            return self.pridectEmotion(frame_raw[y:y + h, x:x + w])
        else:
            return "未检测到人脸！"
            # self.ui.emtiontextlabel.setText("未检测到人脸！")

    def pridectEmotion(self, frame):
        img = frame
        img = cv2.cvtColor(img, cv2.COLOR_GRAY2RGB)
        # img = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
        # img = cv2.cvtColor(img, cv2.COLOR_GRAY2RGB)
        img = cv2.resize(img, (224, 224), interpolation=cv2.INTER_LINEAR)
        img = (img[..., ::-1].astype(np.float32)) / 255.0
        img = img.reshape((1, 224, 224, 3))
        pred = self.model.predict(img)
        type_index = np.argmax(pred)
        res ="s"
        if (type_index == 0):
            res = "angry_emoji"
        if (type_index == 1):
            res =  "happy_emoji"
        if (type_index == 2):
            res =  "neutral_emoji"
        if (type_index == 3):
            res =  "sad_emoji"
        if (type_index == 4):
            res=  "surprise_emoji"
        return  res
        # possible = list(emotion_possible[0])
        #
        # self.F.axes.cla()
        # self.F.axes.set_ylim([0, 100])
        # self.F.axes.bar(["angry", "happy", "neutral", "sad", "surprise"], possible,
        #                 color=["r", "gold", "deepskyblue", "slategray", "g"])
        # self.F.axes.set_title("Prediction Probability Distribution")
        # self.F.draw()
    # def initBar(self):
    #     self.F = MyFigure(width=3, height=2, dpi=100)
    #     self.ui.gridLayout.addWidget(self.F)

    def initCamera_timer(self):
        # self.timer_camera = QtCore.QTimer()
        self.timer_camera.start(10)
        self.timer_camera.timeout.connect(self.showCamera)

    def showCamera(self):
        
        flag, self.image = self.cap.read()
        # show = cv2.resize(self.image, (160, 150))
        # show = cv2.cvtColor(show, cv2.COLOR_BGR2RGB)
        # showImage = QtGui.QImage(show.data, show.shape[1], show.shape[0], QtGui.QImage.Format_RGB888)
        # self.ui.Cameralabel.setPixmap(QtGui.QPixmap.fromImage(showImage))

        self.getDetectCamera(self.image)



    def initEmojis(self):
        shapee = (160, 150)
        self.angry_emoji = cv2.imread("./emoji_pics/angry.png")
        self.angry_emoji = cv2.cvtColor(self.angry_emoji, cv2.COLOR_BGR2RGB)
        self.angry_emoji = cv2.resize(self.angry_emoji, shapee)

        self.happy_emoji = cv2.imread("./emoji_pics/happy.png")
        self.happy_emoji = cv2.cvtColor(self.happy_emoji, cv2.COLOR_BGR2RGB)
        self.happy_emoji = cv2.resize(self.happy_emoji, shapee)

        self.neutral_emoji = cv2.imread("./emoji_pics/neutral.png")
        self.neutral_emoji = cv2.cvtColor(self.neutral_emoji, cv2.COLOR_BGR2RGB)
        self.neutral_emoji = cv2.resize(self.neutral_emoji, shapee)

        self.sad_emoji = cv2.imread("./emoji_pics/sad.png")
        self.sad_emoji = cv2.cvtColor(self.sad_emoji, cv2.COLOR_BGR2RGB)
        self.sad_emoji = cv2.resize(self.sad_emoji, shapee)

        self.surprise_emoji = cv2.imread("./emoji_pics/surprise.png")
        self.surprise_emoji = cv2.cvtColor(self.surprise_emoji, cv2.COLOR_BGR2RGB)
        self.surprise_emoji = cv2.resize(self.surprise_emoji, shapee)



    def startRunning(self):
        if (self.running_flag):
            return 1
        self.running_flag = True
        self.start()

    def stopRunning(self):
        self.running_flag = False
        time.sleep(1)
        self.ui.emtiontextlabel.setText("检测暂停！")

    def run(self):
        fps = 30 #这个是帧数参数，大家可以根据需要自行设定！
        self.cap = cv2.VideoCapture(0)
        while (self.running_flag):
            self.showCamera()
            time.sleep(1 / fps)


# class MyFigure(FigureCanvas):
#     def __init__(self, width, height, dpi):
#         self.fig = Figure(figsize=(width, height), dpi=dpi)
#         super(MyFigure, self).__init__(self.fig)
#         self.axes = self.fig.add_subplot(111)
#
#     def refresh(self):
#         super(MyFigure, self).__init__(self.fig)

# def getModel(self):
#     self.camera_thread=Camera_Thread_class()
#     self.camera_thread.start()
#     return self.camera_thread

camera = Camera_Thread_class()