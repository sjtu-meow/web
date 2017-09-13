package me.sjtumeow.meow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {

    /*private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;*/

    @Before
    public void setupMockMvc() {
        //mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void testLogin() throws Exception {
        //MockHttpServletRequestBuilder builder = new MockHttpServletRequestBuilder(null, null);
        //mockMvc.perform(builder).andReturn();
        // TODO
    }
}
