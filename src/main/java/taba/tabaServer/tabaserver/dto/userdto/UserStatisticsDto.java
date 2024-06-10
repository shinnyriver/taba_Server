package taba.tabaServer.tabaserver.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserStatisticsDto(
        @JsonProperty("new_users") Long newUsers,
        @JsonProperty("withdraw_users") Long withdrawUsers
) implements Serializable {
    public static UserStatisticsDto of(
            final Long newUsers,
            final Long withdrawUsers
    ) {
        return UserStatisticsDto.builder()
                .newUsers(newUsers)
                .withdrawUsers(withdrawUsers)
                .build();
    }
}
