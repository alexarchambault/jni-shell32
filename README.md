## How to run

From the "MSYS MinGW 64-bit" [msys2](https://www.msys2.org/) shell:
```text
$ mkdir -p target/native
$ gcc -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/win32 src/main/c/jnishell32_Shell32.c -o target/native/jnishell32_Shell32.o
$ gcc -s -shared -o target/native/shell32helper.dll target/native/jnishell32_Shell32.o -municode -lole32
```

From the Windows command:
```text
$ javac src/main/java/jnishell32/Shell32.java
$ java -cp "target\native\shell32helper.dll;target\native;src\main\java" jnishell32.Shell32
```
