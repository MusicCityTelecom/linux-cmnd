package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.OnlineDevices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDevicesRepository extends JpaRepository<OnlineDevices, String> {
}
