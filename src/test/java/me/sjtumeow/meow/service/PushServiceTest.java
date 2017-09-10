package me.sjtumeow.meow.service;

import com.sun.org.apache.bcel.internal.generic.PUSH;
import java.util.ArrayList;
import java.util.List;
import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.model.form.PushForm;
import me.sjtumeow.meow.model.result.CreateResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PushServiceTest {
    @Autowired PushService pushService;
    @Test
    public void create() throws Exception {
        PushForm pf = new PushForm();
        pf.setItemId(1L);
        pf.setItemType(1);
        String message = "Test Push";
        pf.setText(message);
        CreateResult result = pushService.create(pf);

        assertNotNull(result);
    }

}