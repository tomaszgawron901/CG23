#include <stdio.h>
#include <stdlib.h>

#define THRESHOLD 4

#include <unistd.h>
#include <time.h>
#define CLOCK CLOCK_PROCESS_CPUTIME_ID

#define data_t int
#define POSMAP ~((long)1<<63)   // MSB equals 0, all rest 1

struct timespec startTime,endTime;

// Let timer tick!  
void startTimer(){
    clock_gettime(CLOCK,&startTime);
}

// Stop the timer and return elapsed time in milli
double stopTimer(){
    clock_gettime(CLOCK,&endTime);
    return (double)(endTime.tv_sec - startTime.tv_sec-1)*1000 + (double)(1000000000+endTime.tv_nsec - startTime.tv_nsec)/1000000;
}

long seeda = 8589102803;
long seedi = 955411474;

// pairwise independent pseudo random numbers
long cheapRand() {
    seedi+=seeda;
    return seedi&POSMAP;  // always return positive results
}
 
void swap(data_t *a, data_t *b){
    data_t c = *a;
    *a = *b;
    *b = c;
}

void ram_shuff(data_t *A, long lo, long hi){
    //shuffle A
    long size = hi-lo;
    for (long i = size-1 ; i>0 ; i--) {
        long randnum =  cheapRand();
        long ri = randnum%(i+1);
        swap(&A[lo+i],&A[lo+ri]);
    }
}

int main(int argc, char **argv){
    struct timespec ptime,ctime;
    if (argc != 2) {
        printf("Enter the size of the array in Megabytes.\n");
        exit(-1);
    } 
    int logsize=atol(argv[1]);
    if (logsize < 6 || logsize > 32) { 
        printf("Please enter a valid size between 6 and 32 and not %d\n",logsize);
        return -1;
    }

    long size = (long)1 << logsize;

    // allocate memory
    data_t *arr = malloc(size*sizeof(data_t));

    // initialize arr
    for (long i = 0 ; i<size ; i++){
        arr[i] = i;
    }

    // measure the running time of the shuffle
    for (int i=4 ; i<=logsize ; i+=1) {  // divide into pieces of size 2^i and shuffle
        long subsize = (long)1 <<i; 
        startTimer();
        for (long j=0 ; j<=size-subsize ; j+=subsize){
            ram_shuff(arr,j,j+subsize);
        }
        double elapsed = stopTimer();
        printf("logsize %d divided into pieces of log size %d took %f Millis.\n",logsize, i,elapsed);
    }
}
