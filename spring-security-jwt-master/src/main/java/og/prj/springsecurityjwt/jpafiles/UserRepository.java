package og.prj.springsecurityjwt.jpafiles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUserName(String userName);
}
