package gr.hua.dit.ds.springbootdemo.repo;
import gr.hua.dit.ds.springbootdemo.entity.Statement;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface StatementRepository extends JpaRepository<Statement, Long>{
}
