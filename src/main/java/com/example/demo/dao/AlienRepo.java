package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Alien;

//JPARepository extends PagingandSortingrepp which extends CRUDrepo so JpaRepository indirectly extends
//CRUDrepo and gives some more extra features
public interface AlienRepo extends JpaRepository<Alien, Integer> {

}
