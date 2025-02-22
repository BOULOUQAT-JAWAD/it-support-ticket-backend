package com.example.ItSupportTicketBackEnd.repositories;

import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query("SELECT t FROM Ticket t WHERE " +
            "(COALESCE(:userId, NULL) IS NULL OR t.createdBy.id = :userId) AND " +
            "(COALESCE(:id, NULL) IS NULL OR t.id = :id) AND " +
            "(COALESCE(:status, NULL) IS NULL OR t.status = :status)")
    List<Ticket> findUserTickets(
            @Param("userId") Long userId,
            @Param("id") Long id,
            @Param("status") TrackingStatus status);

    @Query("SELECT t FROM Ticket t WHERE " +
            "(COALESCE(:id, NULL) IS NULL OR t.id = :id) AND " +
            "(COALESCE(:status, NULL) IS NULL OR t.status = :status)")
    List<Ticket> findFilteredTickets(@Param("id") Long id,
                                     @Param("status") TrackingStatus status);

}

