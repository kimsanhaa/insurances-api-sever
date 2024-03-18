package contract.management.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestMapperTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void test(){
        int test = testMapper.testSelect();
        Assertions.assertThat(test).isNotNull();
    }
}