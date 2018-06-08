package tako.design.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tako.design.order.OrderReq;
import tako.design.replayPrevention.UuidTimeBoundNonce;


@RestController
@RequestMapping("/order")
@Slf4j
public class Order {

    @Autowired
    UuidTimeBoundNonce repository;

    @PostMapping
    public ResponseEntity<String> processOrder (@RequestBody OrderReq req) {
        log.debug("Order received {}", req.toString());
        if ( !req.isValid() ) {
            log.error("Invalid Order {}", req.toString());
            throw new RuntimeException("Invalid request " + req.toString());
        }

        return ResponseEntity.ok("Done");
    }

    @ExceptionHandler( {Exception.class} )
    public ResponseEntity<String> handleException() {
        return ResponseEntity
                .badRequest()
                .body(Constant.StdErrMsg);
    }
}
