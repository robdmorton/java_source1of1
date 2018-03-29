About Bork
======================================================================

Bork is a very small, cross-platform file encryption utility. It is
written in Java and designed to be included along with the files it
encrypts for long-term storage (eg on CD-R). Its minimal external
dependencies make it fairly futureproof.


Building Bork
----------------------------------------------------------------------

You don't need to build Bork to use it, the JAR file that comes with
the distribution will run on any platform that has Java - you will
only need to do a build if you modify the source code.

To build bork, simply run "build.sh" (Unix) or "build.bat"
(Windows). This will build the JAR file needed to run the
application. To build a ZIP file for redistribution, use "build zip"
which will create the distribution ZIP in the "dist" directory.

A JUnit test suite is provided in
"tst/org/matthew/bork/JUTestBork.java". This will run Bork through its
paces, including a round-trip encrypt/decrypt cycle and a decrypt of a
reference sample created with Bork 1.0.

Note that project files for Eclipse 3.0 are also included with the
distribution.


Running Bork
----------------------------------------------------------------------

To run bork, start a shell session and set the encryption password
into the "BORK_PASSWORD" environment variable. You can then use one of
the scripts in the root directory starting with "bork".

The base bork scripts invoke bork on the file names specified on the
command line: any encrypted ".bork" files are unencrypted, any other
files are encrypted. Encrypted files have the same name as the
original, with ".bork" as their extension: you can mask the file name
too if you wish by adding "-Dbork.names=true" after the "java" command
in the script. See "src/org/matthew/bork/Bork.java" for more
information on the Bork application options.

The "bork_nuke" scripts do the same thing as "bork", but enable "nuke"
mode which safely deletes the original files once encrypted.


Hints
----------------------------------------------------------------------

* Windows users can put a shortcut to the batch files on the desktop
  and drag and drop files from Explorer onto them. This requires that
  BORK_PASSWORD is set as a default environment variable, which may or
  may not be secure enough for your tastes.


More Info
----------------------------------------------------------------------

Extra techincal info is in the Bork source: see
src/org/matthew/bork/Bork.java.


Author
----------------------------------------------------------------------

The author is Matthew Phillips <mattphil@gmail.com>. The project is
hosted on SourceForge at http://sourceforge.net/projects/bork.
