import {TestBed} from '@angular/core/testing';

import {AuthenticationService} from './authentication.service';

describe('AuthenticationService', () => {
  let service: AuthenticationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthenticationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('set the token correct when an admin logs in', () => {
    const userName = "philip"

    service.logIn(userName, "random")

    expect(service.authToken).toEqual(`Bearer ${userName}:admin`)
  });

  it('set the token correct when a user logs in', () => {
    const userName = "someRandomUser"

    service.logIn(userName, "random")

    expect(service.authToken).toEqual(`Bearer ${userName}:user`)
  });
});
