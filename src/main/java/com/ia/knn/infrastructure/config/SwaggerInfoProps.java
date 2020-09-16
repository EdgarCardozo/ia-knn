package com.ia.knn.infrastructure.config;

import java.util.Map;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("swagger")
class SwaggerInfoProps {
  private String version;
  private String title;
  private String description;
  private Map<String, String> contact;

  String getContactName() {
    return (String)this.contact.get("name");
  }

  String getContactUrl() {
    return (String)this.contact.get("url");
  }

  String getContactEmail() {
    return (String)this.contact.get("email");
  }

  @Generated
  public SwaggerInfoProps() {
  }

  @Generated
  public String getVersion() {
    return this.version;
  }

  @Generated
  public String getTitle() {
    return this.title;
  }

  @Generated
  public String getDescription() {
    return this.description;
  }

  @Generated
  public Map<String, String> getContact() {
    return this.contact;
  }

  @Generated
  public void setVersion(String version) {
    this.version = version;
  }

  @Generated
  public void setTitle(String title) {
    this.title = title;
  }

  @Generated
  public void setDescription(String description) {
    this.description = description;
  }

  @Generated
  public void setContact(Map<String, String> contact) {
    this.contact = contact;
  }

  @Generated
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof SwaggerInfoProps)) {
      return false;
    } else {
      SwaggerInfoProps other = (SwaggerInfoProps)o;
      if (!other.canEqual(this)) {
        return false;
      } else {
        label59: {
          Object this$version = this.getVersion();
          Object other$version = other.getVersion();
          if (this$version == null) {
            if (other$version == null) {
              break label59;
            }
          } else if (this$version.equals(other$version)) {
            break label59;
          }

          return false;
        }

        Object this$title = this.getTitle();
        Object other$title = other.getTitle();
        if (this$title == null) {
          if (other$title != null) {
            return false;
          }
        } else if (!this$title.equals(other$title)) {
          return false;
        }

        Object this$description = this.getDescription();
        Object other$description = other.getDescription();
        if (this$description == null) {
          if (other$description != null) {
            return false;
          }
        } else if (!this$description.equals(other$description)) {
          return false;
        }

        Object this$contact = this.getContact();
        Object other$contact = other.getContact();
        if (this$contact == null) {
          if (other$contact != null) {
            return false;
          }
        } else if (!this$contact.equals(other$contact)) {
          return false;
        }

        return true;
      }
    }
  }

  @Generated
  protected boolean canEqual(Object other) {
    return other instanceof SwaggerInfoProps;
  }

  @Generated
  public String toString() {
    String var10000 = this.getVersion();
    return "SwaggerInfoProps(version=" + var10000 + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", contact=" + this.getContact() + ")";
  }
}
