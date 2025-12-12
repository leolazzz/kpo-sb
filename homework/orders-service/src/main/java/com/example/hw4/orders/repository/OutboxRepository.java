    package com.example.hw4.orders.repository;

    import com.example.hw4.orders.entity.Outbox;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface OutboxRepository extends JpaRepository<Outbox, Long> {
        List<Outbox> findBySentFalse();
    }