package fr.utc.miage.shares;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ActionSimpleTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";

    @Test
    void testInitializedConstructor() {
        assertDoesNotThrow(new ActionSimple(FOO_SHARE1));
        
       
    }


    

}
