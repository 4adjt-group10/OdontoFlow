package br.com.odontoflow.infrastructure.scheduler;


import br.com.odontoflow.domain.scheduler.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
}
