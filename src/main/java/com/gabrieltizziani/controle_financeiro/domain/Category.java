package com.gabrieltizziani.controle_financeiro.domain;


import com.gabrieltizziani.controle_financeiro.domain.enums.StatusCategory;
import com.gabrieltizziani.controle_financeiro.domain.enums.TypeCategory;
import com.gabrieltizziani.controle_financeiro.dto.category.CreateCategoryRequest;
import com.gabrieltizziani.controle_financeiro.dto.category.UpdateCategoryRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "category")
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category")
    private String nameCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_category")
    private StatusCategory statusCategory = StatusCategory.ATIVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Category(CreateCategoryRequest createCategoryRequest, User user){
        this.nameCategory = createCategoryRequest.nameCategory();
        this.user = user;
    }

    public void updateCategory(UpdateCategoryRequest updateCategoryRequest){
        if (updateCategoryRequest.nameCategory() != null){
            this.nameCategory = updateCategoryRequest.nameCategory();
        }
    }

    public void inactivateCategory(){
        this.statusCategory = StatusCategory.INATIVA;
    }

    public void activateCategory(){
        this.statusCategory = StatusCategory.ATIVA;
    }



}
