
package com.dev.controller;

import com.dev.pojo.ZipCodesPojo;
import com.dev.service.ZipCodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author reddy_a
 *
 */
@RestController
public class ZipCodeController {
    
    @Autowired
    ZipCodeService  zipCodeService;
    
    // Passing the input as Json.Below is sample . 
   /* [{
    	"start": 94600,
    	"end": 94650
    },
    {
    	"start": 94600,
    	"end": 94620
    },
    {
    	"start": 94630,
    	"end": 94650
    },{
    	"start": 94690,
    	"end": 94699
    }]*/
    
    
    @RequestMapping(value = "/checkZipCodeRangesWithPOST",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> parseAndIterateZipCodesWithPOST(@RequestBody List<ZipCodesPojo> listOfPojos){
        
        return zipCodeService.parseAndIterateZipCodesWithPOST(listOfPojos);
        
    }
    
    
}
