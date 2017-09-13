package me.sjtumeow.meow.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.sjtumeow.meow.model.form.PushForm;
import me.sjtumeow.meow.model.result.CreateResult;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PushServiceTest {

    @Autowired
    PushService pushService;

    @Test
    public void findAll() throws Exception {
        /*List<PushArchive> result = pushService.findAll("99");
        assertEquals(4, result.length());*/
    }

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