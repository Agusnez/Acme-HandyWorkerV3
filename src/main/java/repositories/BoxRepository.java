
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b where b.name='trashBox' and b.actor.id=?1")
	Box findTrashBoxByActorId(int actorId);

	@Query("select b from Box b where b.name='inBox' and b.actor.id=?1")
	Box findInBoxByActorId(int actorId);

	@Query("select b from Box b where b.name='outBox' and b.actor.id=?1")
	Box findOutBoxByActorId(int actorId);

	@Query("select b from Box b where b.name='spamBox' and b.actor.id=?1")
	Box findSpamBoxByActorId(int actorId);

	@Query("select b from Box b where b.actor.id=?1")
	Collection<Box> findAllBoxByActorId(int actorId);
}
