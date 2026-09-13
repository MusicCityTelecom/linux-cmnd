/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.ldaptive.LdapAttribute
 *  org.ldaptive.LdapEntry
 *  org.ldaptive.LdapUtils
 *  org.ldaptive.handler.AbstractEntryHandler
 *  org.ldaptive.handler.LdapEntryHandler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.services.persondir.support.ldap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.LdapEntryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDirectoryLdapEntryHandler
extends AbstractEntryHandler<LdapEntry>
implements LdapEntryHandler {
    public static final int ACCOUNT_DISABLED = 2;
    public static final int LOCKOUT = 16;
    public static final int PASSWORD_EXPIRED = 0x800000;
    protected final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());

    private static String decodeLogonBits(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append((b & 1) > 0 ? "1" : "0");
        sb.append((b & 2) > 0 ? "1" : "0");
        sb.append((b & 4) > 0 ? "1" : "0");
        sb.append((b & 8) > 0 ? "1" : "0");
        sb.append((b & 0x10) > 0 ? "1" : "0");
        sb.append((b & 0x20) > 0 ? "1" : "0");
        sb.append((b & 0x40) > 0 ? "1" : "0");
        sb.append((b & 0x80) > 0 ? "1" : "0");
        return sb.toString();
    }

    public boolean equals(Object o) {
        return o instanceof ActiveDirectoryLdapEntryHandler;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode((int)753, (Object[])new Object[0]);
    }

    public String toString() {
        return "[" + ((Object)((Object)this)).getClass().getName() + "@" + this.hashCode() + "]";
    }

    public LdapEntry apply(LdapEntry ldapEntry) {
        LdapAttribute accountExpires;
        LdapAttribute attr = ldapEntry.getAttribute("userAccountControl");
        if (attr != null) {
            int uac = Integer.parseInt(attr.getStringValue());
            if ((uac & 0x10) == 16) {
                this.logger.warn("Account is disabled with UAC {} for entry {}", (Object)uac, (Object)ldapEntry);
                return null;
            }
            if ((uac & 2) == 2) {
                this.logger.warn("Account is disabled with UAC {} for entry {}", (Object)uac, (Object)ldapEntry);
                return null;
            }
            if ((uac & 0x800000) == 0x800000) {
                this.logger.warn("Account has expired");
                return null;
            }
        }
        if ((accountExpires = ldapEntry.getAttribute("accountExpires")) != null) {
            long adDate = Long.parseLong(ldapEntry.getAttribute("accountExpires").getStringValue());
            this.logger.debug("Current active directory account expiration date {}", (Object)adDate);
            if (adDate > 0L) {
                GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
                cal.set(1601, 0, 1, 0, 0);
                long converted = adDate / 10000L;
                Long timeStamp = converted + cal.getTime().getTime();
                Date date = new Date(timeStamp);
                LocalDateTime accountExpiresDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime now = LocalDateTime.now();
                this.logger.debug("Now: {}, account expires at {}", (Object)now, (Object)accountExpiresDate);
                if (accountExpiresDate.isBefore(now)) {
                    this.logger.warn("Account has expired with date {}", (Object)accountExpiresDate);
                    return null;
                }
            }
        }
        if (!this.isValidLogonHour(ldapEntry)) {
            this.logger.warn("Logon Hours are invalid and no attributes will be used");
            return null;
        }
        return ldapEntry;
    }

    protected boolean isValidLogonHour(LdapEntry attr) {
        if (attr.getAttribute("logonHours") != null) {
            byte[] raw = attr.getAttribute("logonHours").getBinaryValue();
            DayOfWeek[] days = new DayOfWeek[]{DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY};
            ArrayList<String> ret = new ArrayList<String>();
            for (int day = 0; day < days.length; ++day) {
                byte[] vBits = day == 6 ? new byte[]{raw[19], raw[20], raw[0]} : new byte[]{raw[day * 3], raw[day * 3 + 1], raw[day * 3 + 2]};
                StringBuilder sb = new StringBuilder();
                for (int b = 0; b < 3; ++b) {
                    sb.append(ActiveDirectoryLdapEntryHandler.decodeLogonBits(vBits[b]));
                }
                ret.add(sb.toString());
            }
            String[] result = new String[ret.size()];
            ret.toArray(result);
            DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
            int currentHour = LocalDateTime.now().getHour() - 1;
            if (currentHour < 0) {
                currentHour = 0;
            }
            this.logger.debug("Current day {}, current hour {}", (Object)currentDay, (Object)currentHour);
            for (int day = 0; day < days.length; ++day) {
                if (days[day] != currentDay) continue;
                String validHours = result[day];
                this.logger.debug("Valid hours are {}", (Object)validHours);
                String hourEnabled = String.valueOf(validHours.charAt(currentHour));
                this.logger.debug("Hour enabled at {} is {}", (Object)currentHour, (Object)hourEnabled);
                if (hourEnabled.equalsIgnoreCase("1")) continue;
                this.logger.warn("Invalid login hour");
                return false;
            }
            return true;
        }
        return true;
    }
}

