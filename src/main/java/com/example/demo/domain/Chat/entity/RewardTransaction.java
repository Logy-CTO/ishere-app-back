package com.example.demo.domain.Chat.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "RewardTransactions")
public class RewardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Post_id")
    private Long postId;

    @Column(name = "Reword")
    private Double reward;

    @Column(name = "SenderPhoneNumber")
    private String senderPhoneNumber;

    @Column(name = "ReceiverPhoneNumber")
    private String receiverPhoneNumber;
}
