
package com.dev.pojo;

import java.io.Serializable;

/**
 * @author reddy_a
 *
 */
public class ZipCodesPojo implements Serializable{
    
    private int start;
    
    private int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
    
    
}
