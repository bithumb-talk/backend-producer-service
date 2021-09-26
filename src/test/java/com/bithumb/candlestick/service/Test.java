//package com.bithumb.candlestick.service;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.*;
//import static org.mockito.Mockito.times;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.tutorial.jwtsecurity.auth.authentication.AuthInfo;
//import com.tutorial.jwtsecurity.auth.repository.RefreshTokenRepository;
//import com.tutorial.jwtsecurity.user.controller.dto.DeleteUserTarget;
//import com.tutorial.jwtsecurity.user.controller.dto.ModifyNicknameTarget;
//import com.tutorial.jwtsecurity.user.controller.dto.ModifyPasswordTarget;
//import com.tutorial.jwtsecurity.user.entity.Authority;
//import com.tutorial.jwtsecurity.user.entity.User;
//import com.tutorial.jwtsecurity.user.repository.UserRepository;
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    //https://doyoung.tistory.com/12
//    //todo. InjectMocks 사용x
//
//    //InjectMocks 목을 주입하고자 하는 클래스(service)
//    //Mock은 가짜 객체
//
//    // 이렇게 가짜 객체를 사용하는 이유는 뭘까?
//    // 통합테스트가 아닌 단위테스트를 하는 이유는 뭘까?
//    // 1. 통합테스트는 데이터베이스 의존적이다
//    // 2. 통합테스트는 동작시간이 너무 오래걸린다.
//    // 3. 즉, 데이터베이스를 사용하지 않고 동작을 검증하기 위한 테스트가 단위테스트 이다.
//
//    // 가짜 객체(목)을 주입하려는 클래스
//    @InjectMocks
//    UserServiceImpl userService;
//    // 가짜 객체
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    PasswordEncoder passwordEncoder;
//    @Mock
//    RefreshTokenRepository refreshTokenRepository;
//
//
//    //dto
//    final User user = User.builder()
//            .id(1l)
//            .userId("bithumb")
//            .password("root")
//            .nickname("nickname")
//            .authority(Authority.ROLE_USER)
//            .build();
//
//    //dto
//    final ModifyPasswordTarget modifyPasswordTarget = ModifyPasswordTarget.builder()
//            .id(1l)
//            .password("rootUser")
//            .build();
//
//    //dto
//    final ModifyNicknameTarget modifyNicknameTarget = ModifyNicknameTarget.builder()
//            .id(1l)
//            .nickname("nickname")
//            .build();
//
//    //dto
//    final DeleteUserTarget deleteUserTarget = DeleteUserTarget.builder()
//            .id(1l)
//            .password("root")
//            .build();
//
//    //dto
//    final AuthInfo authInfo = AuthInfo.UserOf(1l);
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Test
//    @DisplayName("성공테스트 - 패스워드 변경")
//    void changePassword() {
//        Optional<User>  optionalUser = Optional.ofNullable(user);
//
//        // given
//        given(userRepository.findById(any())).willReturn(optionalUser);
//        given(passwordEncoder.encode(any())).willReturn("user");
//        given(userRepository.save(any())).willReturn(user);
//
//        // when
//        userService.changePassword(modifyPasswordTarget,authInfo);
//
//        //then
//        then(userRepository).should(times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 패스워드 변경 / 아이디 존재 x")
//    void changePasswordUserIdNotExist() {
//        // given
//        given(userRepository.findById(any())).willReturn(null);
//
//        // then
//        assertThrows(NullPointerException.class, () -> userService.changePassword(modifyPasswordTarget,authInfo));
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 패스워드 변경 / 아이디 불일치")
//    void changePasswordUserIdNotMatch() {
//        //dto
//        final ModifyPasswordTarget failModifyPassword = ModifyPasswordTarget.builder()
//                .id(2l)
//                .password("rootUser")
//                .build();
//
//        // then
//        assertThrows(SecurityException.class, () -> userService.changePassword(failModifyPassword,authInfo));
//    }
//
//    @Test
//    @DisplayName("성공테스트 - 닉네임 변경")
//    void changeNickname() {
//        Optional<User>  optionalUser = Optional.ofNullable(user);
//
//        // given
//        given(userRepository.findById(any())).willReturn(optionalUser);
//        given(userRepository.save(any())).willReturn(user);
//
//        // when
//        userService.changeNickname(modifyNicknameTarget,authInfo);
//
//        //then
//        then(userRepository).should(times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 닉네임 변경 아이디 존재 x")
//    void changeNicknameUserIdNotExist() {
//        // given
//        given(userRepository.findById(any())).willReturn(null);
//
//        // then
//        assertThrows(NullPointerException.class, () -> userService.changeNickname(modifyNicknameTarget,authInfo));
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 닉네임 변경 / 아이디 불일치")
//    void changeNicknameUserIdNotMatch() {
//        //dto
//        final ModifyNicknameTarget failModifyNickname = ModifyNicknameTarget.builder()
//                .id(2l)
//                .nickname("nickname")
//                .build();
//
//        // then
//        assertThrows(SecurityException.class, () -> userService.changeNickname(failModifyNickname,authInfo));
//    }
//
//    @Test
//    @DisplayName("성공테스트 - 회원 삭제")
//    void deleteUser() {
//        Optional<User>  optionalUser = Optional.ofNullable(user);
//
//        // given
//        given(userRepository.findById(any())).willReturn(optionalUser);
//        given(passwordEncoder.matches(any(),any())).willReturn(true);
//
//        // when
//        userService.deleteUser(deleteUserTarget,authInfo);
//
//        //then
//        then(userRepository).should(times(1)).deleteById(any());
//        then(refreshTokenRepository).should(times(1)).deleteById(any());
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 회원 삭제 / 아이디 존재 x")
//    void deleteUserButIdNotExist() {
//        // given
//        given(userRepository.findById(any())).willReturn(null);
//
//        // then
//        assertThrows(NullPointerException.class, () -> userService.deleteUser(deleteUserTarget,authInfo));
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 회원 삭제 / 아이디 불일치")
//    void deleteUserButIdNotMatch() {
//        //dto
//        final DeleteUserTarget faildeleteUser = DeleteUserTarget.builder()
//                .id(2l)
//                .password("root")
//                .build();
//
//        Optional<User>  optionalUser = Optional.ofNullable(user);
//
//        // given
//        given(userRepository.findById(any())).willReturn(optionalUser);
//
//        // then
//        assertThrows(SecurityException.class, () -> userService.deleteUser(faildeleteUser,authInfo));
//    }
//
//    @Test
//    @DisplayName("실패테스트 - 회원 삭제 / 패스워드 불일치")
//    void deleteUserButPasswordNotMatch() {
//        //dto
//        final DeleteUserTarget faildeleteUser = DeleteUserTarget.builder()
//                .id(1l)
//                .password("failPassword")
//                .build();
//
//        Optional<User>  optionalUser = Optional.ofNullable(user);
//
//        // given
//        given(userRepository.findById(any())).willReturn(optionalUser);
//
//        // then
//        assertThrows(SecurityException.class, () -> userService.deleteUser(faildeleteUser,authInfo));
//    }
//
//}