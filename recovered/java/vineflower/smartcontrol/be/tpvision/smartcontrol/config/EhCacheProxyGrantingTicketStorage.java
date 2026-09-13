package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.messages.config.eh_cache_proxy_granting_ticket_storage.ConstructorMessages;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class EhCacheProxyGrantingTicketStorage implements ProxyGrantingTicketStorage {
   private static final Logger logger = LoggerFactory.getLogger(EhCacheProxyGrantingTicketStorage.class);
   private Ehcache ehcache;

   public EhCacheProxyGrantingTicketStorage(final Ehcache ehcache) {
      Assert.notNull(ehcache, ConstructorMessages.EH_CACHE_CAN_NOT_BE_NULL);
      this.ehcache = ehcache;
   }

   @Override
   public String retrieve(final String proxyGrantingTicketIou) {
      if (CommonUtils.isBlank(proxyGrantingTicketIou)) {
         return null;
      } else {
         Element element = this.ehcache.get(proxyGrantingTicketIou);
         if (element == null) {
            logger.info("No Proxy Ticket found for [{}].", proxyGrantingTicketIou);
            return null;
         } else {
            this.ehcache.remove(proxyGrantingTicketIou);
            String proxyGrantingTicket = (String)element.getObjectValue();
            logger.debug("Returned ProxyGrantingTicket of [{}]", proxyGrantingTicket);
            return proxyGrantingTicket;
         }
      }
   }

   @Override
   public void save(final String proxyGrantingTicketIou, final String proxyGrantingTicket) {
      logger.debug("Saving ProxyGrantingTicketIOU and ProxyGrantingTicket combo: [{}, {}]", proxyGrantingTicketIou, proxyGrantingTicket);
      Element element = new Element(proxyGrantingTicketIou, proxyGrantingTicket);
      this.ehcache.put(element);
   }

   @Override
   public void cleanUp() {
      this.ehcache.evictExpiredElements();
   }
}
