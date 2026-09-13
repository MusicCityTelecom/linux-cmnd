/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CommentType", propOrder={"comment"})
public class CommentType {
    @XmlElement(name="Comment", required=true)
    protected List<Comment> comment;

    public List<Comment> getComment() {
        if (this.comment == null) {
            this.comment = new ArrayList<Comment>();
        }
        return this.comment;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Comment
    extends ParagraphType {
        @XmlAttribute(name="CommentOriginatorCode")
        protected String commentOriginatorCode;
        @XmlAttribute(name="GuestViewable")
        protected Boolean guestViewable;

        public String getCommentOriginatorCode() {
            return this.commentOriginatorCode;
        }

        public void setCommentOriginatorCode(String value) {
            this.commentOriginatorCode = value;
        }

        public Boolean isGuestViewable() {
            return this.guestViewable;
        }

        public void setGuestViewable(Boolean value) {
            this.guestViewable = value;
        }
    }
}

