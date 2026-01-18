package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> findSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		LocalDate[] range = resolveMinMax(minDate, maxDate);

        return repository.findSalesReport(range[0], range[1], name, pageable);

	}

	public List<SellerMinDTO> findSalesSummaryBySeller(LocalDate minDate, LocalDate maxDate) {
		LocalDate[] range = resolveMinMax(minDate, maxDate);

        return repository.findSalesSummaryBySeller(range[0], range[1]);

	}

	private LocalDate[] resolveMinMax(LocalDate minDate, LocalDate maxDate) {
		LocalDate max = (maxDate == null)
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: maxDate;

		LocalDate min = (minDate == null)
				? max.minusYears(1L)
				: minDate;

		return new LocalDate[]{min, max};
	}

}
