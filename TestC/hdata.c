/*
 * hdata.c
 *
 *  Created on: Oct 10, 2013
 *      Author: dc259
 */



# define LAST 19
int main()
    {
        int i, sum = 0;

        for ( i = 1; i <= LAST; i++ ) {
          sum += i;
        } /*-for-*/
        printf("sum = %d\n", sum);

        return 0;
    }
