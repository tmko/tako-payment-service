package tako.design.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tako.design.replayPrevention.UuidTimeBoundNonce;

@RestController
@RequestMapping("/nonce")
public class Nonce {

    @Autowired
    UuidTimeBoundNonce uuidApi;

    @GetMapping
    public ResponseEntity<String> getNonce () {
        String uuid = uuidApi.create();
        return ResponseEntity.ok(uuid);
    }

    @ExceptionHandler( {Exception.class} )
    public ResponseEntity<String> handleException() {
        return ResponseEntity
                .badRequest()
                .body(Constant.StdErrMsg);
    }
}
