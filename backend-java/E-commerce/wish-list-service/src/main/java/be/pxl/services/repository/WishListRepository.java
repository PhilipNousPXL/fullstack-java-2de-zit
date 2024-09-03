package be.pxl.services.repository;

import be.pxl.services.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, String> {
}
