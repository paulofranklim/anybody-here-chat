package com.anybodyherechat.entity;


import com.anybodyherechat.model.UserRequestBody;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USERS")
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false, name = "password_hash")
        private String password;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false, name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        private  LocalDateTime createDatetime;

        @Column(nullable = true, name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime updateDatetime;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public LocalDateTime getCreateDatetime() {
                return createDatetime;
        }

        public void setCreateDatetime(LocalDateTime createDatetime) {
                this.createDatetime = createDatetime;
        }

        public LocalDateTime getUpdateDatetime() {
                return updateDatetime;
        }

        public void setUpdateDatetime(LocalDateTime updateDatetime) {
                this.updateDatetime = updateDatetime;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        @Override
        public final boolean equals(Object o) {
                if (this == o) {
                        return true;
                }
                if (!(o instanceof UserEntity that)) {
                        return false;
                }

            return id.equals(that.id) && username.equals(that.username);
        }

        @Override
        public int hashCode() {
                int result = id.hashCode();
                result = 31 * result + username.hashCode();
                return result;
        }

        @Override
        public String toString() {
                final StringBuilder sb = new StringBuilder("UserEntity{");
                sb.append("id=").append(id);
                sb.append(", username='").append(username).append('\'');
                sb.append(", createDatetime=").append(createDatetime);
                sb.append(", updateDatetime=").append(updateDatetime);
                sb.append('}');
                return sb.toString();
        }

        public static UserEntity toModel(UserRequestBody newUser, PasswordEncoder encrypt){
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(newUser.getUsername());
                userEntity.setPassword(encrypt.encode(newUser.getPassword()));
                userEntity.setEmail(newUser.getEmail());
                userEntity.setCreateDatetime(LocalDateTime.now());
                return userEntity;
        }

}
