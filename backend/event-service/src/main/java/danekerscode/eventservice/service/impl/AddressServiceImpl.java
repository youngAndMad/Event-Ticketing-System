package danekerscode.eventservice.service.impl;

import danekerscode.eventservice.model.Address;
import danekerscode.eventservice.repository.AddressRepository;
import danekerscode.eventservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }
}
