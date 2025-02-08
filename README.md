# Brick Breaker
A recreation of Atari Breakout written in Java using libGDX.
I have uploaded native releases in the releases section.
**There is no multiplayer yet, only sending hello world messages as a test.**

[itch.io](jthecoder12.itch.io/brick-breaker)

**Controls:**
A to move left, D to move right. M to open the menu. In the menu, there is a mouse mode. Use mouse mode to control the paddle with your mouse. There is also controller support. You just need to make sure that your controller is connected to your computer. If your controller can vibrate, it will vibrate if the ball hits something other than the ground. There is also a challenge mode for controllers that can vibrate which will cause the controller to vibrate very fast while the setting is turned on.

Web Version: [Click Here](https://jthecoder12.github.io/BrickBreakerNew)
The web version won't be as good as I had to minimize it for TeaVM support so there is no multiplayer, controller support, or ImGui menu. The positioning may also be off as the game takes up the entire browser page. Also expect the web version to get less bug fixes and later updates.

Images:
![titlescreen](https://github.com/user-attachments/assets/1b193243-0a41-447f-9fdb-51e1d2349ca6)
![singleplayer](https://github.com/user-attachments/assets/d0bbf897-b76b-4747-ae98-f3927d525b5f)
![multiplayer](https://github.com/user-attachments/assets/4f3c6017-49c2-4652-8d05-a629fd0ac01d)
![menu](https://github.com/user-attachments/assets/7e7eeccb-da6d-487a-b3a1-b80f8979b56b)


Singleplayer gameplay:

https://github.com/user-attachments/assets/02094a3f-aed4-4348-b6c6-212bdbc6f4ea

To build yourself:
Download the source code,
Run <code>./gradlew -p lwjgl3 dist</code> on Linux/MacOS or <code>.\gradlew.bat -p lwjgl3 dist</code> on Windows,
The .jar file should be in **lwjgl3/build/libs**.
