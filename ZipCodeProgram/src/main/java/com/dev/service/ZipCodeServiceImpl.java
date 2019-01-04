
package com.dev.service;

import com.dev.util.ZipCodeUtil;
import com.dev.pojo.ZipCodesPojo;
import com.dev.util.ZipCodeChecker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author reddy_a
 *
 */
@Service("ZipCodeService")
public class ZipCodeServiceImpl implements ZipCodeService{
    
    
    @Override
    public ResponseEntity<?> parseAndIterateZipCodesWithPOST(List<ZipCodesPojo> listOfPojos) {
        
        System.out.println(" Raw input ranges ");
        System.out.println("");
        
        List<ZipCodeUtil> inputList = new ArrayList<>();
        
        for(ZipCodesPojo irange : listOfPojos){
            System.out.println(irange.getStart()+"  ,   "+irange.getEnd());
            int[] arrays = new int[2];
            arrays[0] = irange.getStart();
            arrays[1] = irange.getEnd();
            inputList.add(new ZipCodeUtil(arrays));             
        }
        
        System.out.println("");
        
        
        List<ZipCodeUtil> excludes = ZipCodeChecker.consolidate(inputList);
        
        List<ZipCodesPojo> listOfCodes = new ArrayList<>();
        
        System.out.println(" ouput ranges ");
        for(ZipCodeUtil range : excludes){
            System.out.println(range.getStart()+"   ,   "+range.getEnd());
            ZipCodesPojo code = new ZipCodesPojo();
            code.setStart(range.getStart());
            code.setEnd(range.getEnd());
            listOfCodes.add(code);
        }
             
        return new ResponseEntity<>(listOfCodes,HttpStatus.OK);
    
    }
    
}
