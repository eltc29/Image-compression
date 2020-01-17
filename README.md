## Image Compression Program

Instructions:
- Clone repo to your computer.
- Open the terminal in the folder you downloaded the files.
- Run `make makefile.txt`.
- Run `./roundtrip INPUTFILE OUTPUTFILE THRESHOLD MAXDEPTH`.
  - INPUTFILE is test.pgm.
  - OUTPUTFILE, you give it a name with .pgm extension (for e.g. out.pgm).
  - THRESHOLD is a value such that the image cannot be broken down any further once the threshold is reached.
  - MAXDEPTH is a the maximum number of times user wants to subdivide the pixel squares.
- Play between values of THRESHOLD and MAXDEPTH to obtain interesting output.
- To view output image, go to http://paulcuth.me.uk/netpbm-viewer/ and insert output image there.
