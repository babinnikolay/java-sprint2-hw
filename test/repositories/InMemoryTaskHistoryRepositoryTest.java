package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

class InMemoryTaskHistoryRepositoryTest extends AbstractTaskHistoryRepositoryTest<InMemoryTaskHistoryRepository>{

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskHistoryRepository = new InMemoryTaskHistoryRepository(serviceStub);
    }
}