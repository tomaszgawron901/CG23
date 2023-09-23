import matplotlib.pyplot as plt
import math
import sys

x = []
y = []
for line in open(sys.argv[1], 'r'):
	lines = [i for i in line.split()]
	x.append(lines[0])
	y.append(float(lines[1]))
	

#fig, (ax1, ax2,ax3) = plt.subplots(3)
ny= [y[i]/y[0] for i in range(len(y))]
plt.plot(x, ny, marker = 'o', c = 'g')
#ax1.plot(x,ly)
#ax1.set_title('Raw running time')
#theo= [y[i]/float(x[i])/2**float(x[i]) for i in range(len(x))]
#ax2.plot(x,theo)
#ax2.set_title('Div. n log n')
#n= [y[i]/2**float(x[i]) for i in range(len(x))]
#ax3.plot(x,n)
#ax3.set_title('Div. n ')

plt.show()
