/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.api.ReceptionSocket;
import com.tpvision.smartinstall.dao.FutureCheckInRepository;
import com.tpvision.smartinstall.dao.core.FutureCheckIn;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FutureCheckInManager {
    @Autowired
    private FutureCheckInRepository futureCheckInRepository;

    public void save(FutureCheckIn futureCheckIn) {
        this.futureCheckInRepository.save(futureCheckIn);
        ReceptionSocket.notifyPmsGuestInfoUpdate();
    }

    public List<FutureCheckIn> findFutureCheckInListByRoomId(String roomId) {
        return this.futureCheckInRepository.findByRoomid(roomId);
    }

    public FutureCheckIn loadByKey(int futureId) {
        return this.futureCheckInRepository.findById(futureId).orElse(null);
    }

    public void deleteFutureCheckIn(FutureCheckIn futureCheckIn) {
        this.futureCheckInRepository.delete(futureCheckIn);
        ReceptionSocket.notifyPmsGuestInfoUpdate();
    }

    public List<FutureCheckIn> findRequiredCheckInGuestInfo(String checkinTime) {
        return this.futureCheckInRepository.findRequiredCheckInGuestInfo(checkinTime);
    }

    public Page<FutureCheckIn> findFutureCheckInsByKeyword(String checkinTime, String keyword, Pageable pageable) {
        Sort.Order order;
        Sort sort = pageable.getSort();
        if (sort.isSorted() && StringUtils.equalsIgnoreCase((order = sort.iterator().next()).getProperty(), "roomid")) {
            return this.findByKeywordOrderByRoomid(checkinTime, keyword, order.getDirection(), pageable);
        }
        Specification<FutureCheckIn> specs = this.getQuerySpecification(checkinTime, keyword);
        return this.futureCheckInRepository.findAll(specs, pageable);
    }

    private Page<FutureCheckIn> findByKeywordOrderByRoomid(String checkinTime, String keyword, Sort.Direction sortDirection, Pageable pageable) {
        long totalCount = this.futureCheckInRepository.count(this.getQuerySpecification(checkinTime, keyword));
        List<Object> futureCheckInList = Collections.emptyList();
        if (totalCount > 0L) {
            int startIndex = (int)pageable.getOffset();
            int pageSize = pageable.getPageSize();
            futureCheckInList = sortDirection == Sort.Direction.ASC ? this.futureCheckInRepository.findByKeywordOrderByRoomidAsc(checkinTime, keyword, startIndex, pageSize) : this.futureCheckInRepository.findByKeywordOrderByRoomidDesc(checkinTime, keyword, startIndex, pageSize);
        }
        return new PageImpl<FutureCheckIn>(futureCheckInList, pageable, totalCount);
    }

    private Specification<FutureCheckIn> getQuerySpecification(String checkinTime, String keyword) {
        return (root, query, cb) -> {
            Predicate keywordSpec = null;
            if (StringUtils.isNotBlank(keyword)) {
                String matchKeyword = "%" + keyword + "%";
                Predicate roomidLike = cb.like(root.get("roomid").as(String.class), matchKeyword);
                Predicate guestNameLike = cb.like(root.get("guestName").as(String.class), matchKeyword);
                Predicate checkinTimeLike = cb.like(root.get("checkinTime").as(String.class), matchKeyword);
                Predicate checkoutTimeLike = cb.like(root.get("checkoutTime").as(String.class), matchKeyword);
                keywordSpec = cb.or(roomidLike, guestNameLike, checkoutTimeLike, checkinTimeLike);
            }
            Predicate checkinTimeSpec = null;
            if (StringUtils.isNotBlank(checkinTime)) {
                String matchCheckinTime = checkinTime + "%";
                checkinTimeSpec = cb.like(root.get("checkinTime").as(String.class), matchCheckinTime);
            }
            if (keywordSpec == null && checkinTimeSpec == null) {
                return null;
            }
            if (keywordSpec != null && checkinTimeSpec == null) {
                return keywordSpec;
            }
            if (keywordSpec == null && checkinTimeSpec != null) {
                return checkinTimeSpec;
            }
            return cb.and((Expression<Boolean>)keywordSpec, (Expression<Boolean>)checkinTimeSpec);
        };
    }
}

