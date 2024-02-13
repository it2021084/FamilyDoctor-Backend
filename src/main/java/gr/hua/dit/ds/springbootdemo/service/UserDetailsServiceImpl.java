package gr.hua.dit.ds.springbootdemo.service;

import gr.hua.dit.ds.springbootdemo.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import gr.hua.dit.ds.springbootdemo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user =userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No User with username:"+username));
        return  UserDetailsImpl.build(user);
    }

}