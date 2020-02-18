package com.ebb.pma.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ebb.pma.entities.UserAccount;

public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long>{ /** extends CrudRepository<UserAccount, Long>{ */

}
