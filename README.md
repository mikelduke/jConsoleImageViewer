# jConsoleImageViewer
Java Command Line Image Viewer for RPi

* Loads an Image and writes it to the framebuffer /dev/fb1
* Works with the Adafruit PiTFT

Uses JavaFrameBuffer fork https://github.com/mikelduke/JavaFrameBuffer from https://github.com/ttww/JavaFrameBuffer for framebuffer access.

## Usage
Run the ```./rpi.sh``` script to build and scp to a pi then ```./viewImage.sh [filename]``` to run. 

## Install Process
1. Build with Gradle

```./gradlew build fatjar```

2. Copy to pi

```scp build/libs/jConsoleImageViewer-all-1.0.0.jar pi@[ip address]:~/```

3. Run with 

```java -Djava.library.path=. -jar jConsoleImageViewer-all-1.0.0.jar [filename]```
