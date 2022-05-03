package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

class InMemoryTaskRepositoryTest extends AbstractTaskRepositoryTest<InMemoryTaskRepository> {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskRepository = new InMemoryTaskRepository(serviceStub);
    }

}