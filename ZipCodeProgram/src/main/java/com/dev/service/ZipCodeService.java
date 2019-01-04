
package com.dev.service;

import com.dev.util.ZipCodeUtil;
import com.dev.pojo.ZipCodesPojo;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * @author reddy_a
 *
 */
public interface ZipCodeService {
    
    
    public ResponseEntity<?> parseAndIterateZipCodesWithPOST(List<ZipCodesPojo> listOfPojos);
    
    
}
