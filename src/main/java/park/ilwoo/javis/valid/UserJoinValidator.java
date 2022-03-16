package park.ilwoo.javis.valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import park.ilwoo.javis.user.entity.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserJoinValidator implements Validator {
    //  한글은 YML에서 속성 가져오는게 안되는듯..
    private Map<String, String> permittedMap = new HashMap<String, String>(){{
        put("홍길동", "860824-1655068");
        put("김둘리", "921108-1582816");
        put("마징가", "880601-2455116");
        put("베지터", "910411-1656116");
        put("손오공", "820326-2715702");
    }};

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //  회원가입 하려는 user 정보 가져오기
        User joinUser =  (User) target;

        //  1. 가입할 수 있는 회원의 이름이 아닐경우
        String userName = joinUser.getName();
        if(!permittedMap.keySet().contains(userName)) {
            errors.rejectValue("name", "invalid.name", "가입할 수 없는 대상입니다.");
            return;
        }

        //  2. 이름과 주민번호 매칭이 안될경우
        String regNo = joinUser.getRegNo();
        if(!permittedMap.get(userName).equals(regNo)) errors.rejectValue("regNo", "invalid.regNo", "주민번호가 맞지 않습니다.");

    }
}
