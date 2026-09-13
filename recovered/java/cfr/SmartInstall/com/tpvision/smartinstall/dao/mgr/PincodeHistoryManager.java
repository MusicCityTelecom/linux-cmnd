/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.api.MyChoiceController;
import com.tpvision.smartinstall.dao.PincodeHistoryRepository;
import com.tpvision.smartinstall.dao.core.PincodeHistory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PincodeHistoryManager {
    @Autowired
    private PincodeHistoryRepository pincodeHistoryRepository;

    public void savePincodeHistory(PincodeHistory pincodeHistory) {
        this.pincodeHistoryRepository.save(pincodeHistory);
    }

    public List<PincodeHistory> findLatestCountPincodeHistory(int limit) {
        return this.pincodeHistoryRepository.findByOrderByIdDesc(limit);
    }

    @Transactional
    public void deactivateAllNotExpiredPincodeForRoom(String roomId) {
        this.pincodeHistoryRepository.deactivateAllNotExpiredPincodeForRoom(roomId);
    }

    @Transactional
    public void deactivatePackageNotExpiredPincodeForRoom(String roomId, String packageName) {
        this.pincodeHistoryRepository.deactivatePackageNotExpiredPincodeForRoom(roomId, packageName);
    }

    public PincodeHistory findById(int id) {
        return this.pincodeHistoryRepository.findById(id).orElse(null);
    }

    public List<PincodeHistory> findValidPincdeHistoryForRoom(String roomId) {
        return this.pincodeHistoryRepository.findByRommIdAndStopTime(roomId, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    public List<PincodeHistory> findValidPincdeHistoryForRfDevice(String roomId, String packageName) {
        return this.pincodeHistoryRepository.findByRommIdAndPackageNameAndStopTime(roomId, packageName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    public Page<PincodeHistory> findPincodeHistoryByKeyword(MyChoiceController.HistoryType type, String keyword, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("id")));
        if (type == MyChoiceController.HistoryType.ALL) {
            Specification<PincodeHistory> specs = this.getQuerySpecification(keyword);
            return this.pincodeHistoryRepository.findAll(specs, (Pageable)pageable);
        }
        long totalCount = this.pincodeHistoryRepository.countAllActivePincodeHistory(keyword);
        long pageStartIndex = pageable.getOffset();
        List<Object> historyList = Collections.emptyList();
        if (pageStartIndex < totalCount) {
            historyList = this.pincodeHistoryRepository.findPageDataOfActivePincodeHistory(keyword, (int)pageStartIndex, pageSize);
        }
        return new PageImpl<PincodeHistory>(historyList, pageable, totalCount);
    }

    private Specification<PincodeHistory> getQuerySpecification(String keyword) {
        return (root, query, cb) -> {
            if (StringUtils.isNotBlank(keyword)) {
                String matchKeyword = "%" + keyword + "%";
                Predicate roomidLike = cb.like(root.get("roomId").as(String.class), matchKeyword);
                Predicate packageNameLike = cb.like(root.get("packageName").as(String.class), matchKeyword);
                Predicate stopTimeLike = cb.like(root.get("stopTime").as(String.class), matchKeyword);
                Predicate pincodeLike = cb.like(root.get("pincode").as(String.class), matchKeyword);
                return cb.or(roomidLike, packageNameLike, stopTimeLike, pincodeLike);
            }
            return null;
        };
    }
}

