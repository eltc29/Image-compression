#include <stdlib.h>
#include <stdio.h>
#include <math.h>                   /* for lrint */
#include "quad.h"
#include "pgmio.h"

/* Reconstruct image from quadtree.  Write into the Image record that im points
 * to.  Assume that the square region described by the quadtree fits into the
 * Image.
 *
 * Recall that each Quad node already contains position information; respect it,
 * and you will benefit from it too (a secret for successful recursions).
 */
void saveQuad(Image *im, Quad *q)
{
	if (q->children[0]==NULL){
		for (int i=q->tx; i<(q->tx)+q->width; i++){
			for (int j=q->ty; j<(q->ty)+q->width; j++){
				im->data[j*im->sx+i] = q->grayscale;
			}
		}
		return;
	}
	for (int i=0; i<4; i++){
		saveQuad(im, q->children[i]);
	}
}

/* Build quadtree from image.  Splitting stops when maxDepth hits 0 or maximum
 * difference in grayscale is <= threshold.  Do this to only the square region
 * at top-left corner (tx, ty) of width w.  Use heap allocation for all Quad
 * nodes you produce.
 */
Quad *formQuadtree(Image *im, int maxDepth, int threshold, int tx, int ty, int w)
{
	Quad *q=(Quad *)malloc(sizeof(Quad));
	q->tx = tx;
	q->ty = ty;
	q->width = w;
	
	//looking for max grayscale difference in block
	int min_g=(int)(im->data[ty*im->sx+tx]), max_g=(int)(im->data[ty*im->sx+tx]), maxGsDiff;
	for (int i=tx; i<tx+w; i++){ 
		for (int j=ty; j<ty+w; j++){
			if ((int)(im->data[j*im->sx+i]) < min_g)
				min_g = (int)(im->data[j*im->sx+i]);
			else if ((int)(im->data[j*im->sx+i]) > max_g)
				max_g = (int)(im->data[j*im->sx+i]);
		}
	}
	maxGsDiff = max_g - min_g;
	
	//base case
	if (w==1 || maxGsDiff<=threshold || maxDepth==0){
		int sum=0;
		for (int i=tx; i<tx+w; i++){
			for (int j=ty; j<ty+w; j++){ 
				sum+=(int)(im->data[j*im->sx+i]);
			}
		}
		q->grayscale=(unsigned char)(lrint(sum/(w*w)));
		for (int i=0; i<4; i++){
			q->children[i] = NULL;
		}
		return q;
	}
	else{
		//indexes for topleft, topright, bottomleft, bottomright
		int arrX[4] = {tx, tx+w/2, tx, tx+w/2};
		int arrY[4] = {ty, ty, ty+w/2, ty+w/2};
		
		//set children and subdivide
		maxDepth+=-1;
		for (int i=0; i<4; i++){
			q->children[i] = formQuadtree(im, maxDepth, threshold, arrX[i], arrY[i], w/2);
		}
	}
	return q;
}

/* Deallocate all Quad nodes of the given quadtree. Assume that they all came
 * from the heap.
 */
void freeQuadtree(Quad *q)
{
	if (q==NULL){
		free(q);
		return;
	}
	for (int i=0; i<4; i++){
		freeQuadtree(q->children[i]);
	}
	free(q); //free the root
}

