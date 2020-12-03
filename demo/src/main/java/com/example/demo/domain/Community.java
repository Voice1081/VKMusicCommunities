package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "community")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Community {
    @Id
    @Column(name = "community_id", columnDefinition = "bigint")
    private Long communityId;
    @Column(name = "name", columnDefinition = "text")
    private String name;
    @Column(name = "domain", columnDefinition = "text")
    private String domain;
    @Type(type = "list-array")
    @Column(name = "genre", columnDefinition = "TEXT[]")
    private List<String> genres;
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;
    @Column(name = "last_post_time", columnDefinition = "bigint")
    private Long lastPostTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(Long lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    @Override
    public String toString() {
        return "Community{" +
            ", name='" + name + '\'' +
            ", domain='" + domain + '\'' +
            ", communityId=" + communityId +
            ", genres=" + genres +
            ", link='" + link + '\'' +
            ", lastPostTime=" + lastPostTime +
            '}';
    }
}
