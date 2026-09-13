package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.htng._2011b.HTNGProfileMessageRS;
import org.htng._2011b.HTNGProfileMessageStatusNotifRQ;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParagraphType", propOrder = "textOrImageOrURL")
@XmlSeeAlso(
   {
         HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage.class,
         HTNGProfileMessageRS.ProfileMessages.ProfileMessage.class,
         CommentType.Comment.class,
         SpecialRequestType.SpecialRequest.class,
         ProfileType.Comments.Comment.class,
         InvBlockType.BlockDescriptions.BlockDescription.class,
         AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramDescription.class,
         AffiliationInfoType.LoyalPrograms.LoyalProgram.ProgramRestriction.class,
         DescriptionType.class
   }
)
public class ParagraphType {
   @XmlElementRefs(
      {
            @XmlElementRef(name = "Text", namespace = "http://www.opentravel.org/OTA/2003/05", type = JAXBElement.class, required = false),
            @XmlElementRef(name = "Image", namespace = "http://www.opentravel.org/OTA/2003/05", type = JAXBElement.class, required = false),
            @XmlElementRef(name = "URL", namespace = "http://www.opentravel.org/OTA/2003/05", type = JAXBElement.class, required = false),
            @XmlElementRef(name = "ListItem", namespace = "http://www.opentravel.org/OTA/2003/05", type = JAXBElement.class, required = false)
      }
   )
   protected List<JAXBElement<?>> textOrImageOrURL;
   @XmlAttribute(name = "Name")
   protected String name;
   @XmlAttribute(name = "ParagraphNumber")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger paragraphNumber;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;
   @XmlAttribute(name = "CreateDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar createDateTime;
   @XmlAttribute(name = "CreatorID")
   protected String creatorID;
   @XmlAttribute(name = "LastModifyDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastModifyDateTime;
   @XmlAttribute(name = "LastModifierID")
   protected String lastModifierID;
   @XmlAttribute(name = "PurgeDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar purgeDate;

   public List<JAXBElement<?>> getTextOrImageOrURL() {
      if (this.textOrImageOrURL == null) {
         this.textOrImageOrURL = new ArrayList<>();
      }

      return this.textOrImageOrURL;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public BigInteger getParagraphNumber() {
      return this.paragraphNumber;
   }

   public void setParagraphNumber(BigInteger value) {
      this.paragraphNumber = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
   }

   public XMLGregorianCalendar getCreateDateTime() {
      return this.createDateTime;
   }

   public void setCreateDateTime(XMLGregorianCalendar value) {
      this.createDateTime = value;
   }

   public String getCreatorID() {
      return this.creatorID;
   }

   public void setCreatorID(String value) {
      this.creatorID = value;
   }

   public XMLGregorianCalendar getLastModifyDateTime() {
      return this.lastModifyDateTime;
   }

   public void setLastModifyDateTime(XMLGregorianCalendar value) {
      this.lastModifyDateTime = value;
   }

   public String getLastModifierID() {
      return this.lastModifierID;
   }

   public void setLastModifierID(String value) {
      this.lastModifierID = value;
   }

   public XMLGregorianCalendar getPurgeDate() {
      return this.purgeDate;
   }

   public void setPurgeDate(XMLGregorianCalendar value) {
      this.purgeDate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ListItem extends FormattedTextTextType {
      @XmlAttribute(name = "ListItem")
      protected BigInteger listItem;

      public BigInteger getListItem() {
         return this.listItem;
      }

      public void setListItem(BigInteger value) {
         this.listItem = value;
      }
   }
}
