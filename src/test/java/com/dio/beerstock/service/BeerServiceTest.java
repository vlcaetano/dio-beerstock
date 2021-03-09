package com.dio.beerstock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dio.beerstock.builder.BeerDTOBuilder;
import com.dio.beerstock.dto.BeerDTO;
import com.dio.beerstock.entity.Beer;
import com.dio.beerstock.exception.BeerAlreadyRegisteredException;
import com.dio.beerstock.mapper.BeerMapper;
import com.dio.beerstock.repository.BeerRepository;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

	private static final long INVALID_BEER_ID = 1L;
	
	@Mock
	private BeerRepository beerRepository;
	
	private BeerMapper beerMapper = BeerMapper.INSTANCE;
	
	@InjectMocks
	private BeerService beerService;
	
	@Test
	void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
		//given
		BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
		Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);
		
		//when
		when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
		when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);
		
		//then
		BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);
		
		assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
		assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
		assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));
		
		assertThat(createdBeerDTO.getQuantity(), is(greaterThan(2)));
		
		/*assertEquals(expectedBeerDTO.getId(), createdBeerDTO.getId());
		assertEquals(expectedBeerDTO.getName(), createdBeerDTO.getName());*/
	}
}
