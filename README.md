# Scalable prime number calculator based on Akka

How to build
------------
- git clone https://github.com/latuannetnam/prime-number-calculator.git
- mvn package
- The runnable file is target/prime-number-calculator-1.0.jar

General usage
-------------
* -f N: first number to calculate
* -l N: last number to calculate
* -m run_mode: Running mode. There a 4 choice: s (single threaded); t (multi-threaded); m (master node in cluster); w (worker node in cluster)
* -i IP-address: IP address of master node in cluster mode

 
