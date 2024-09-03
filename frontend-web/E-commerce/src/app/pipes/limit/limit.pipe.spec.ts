import {LimitPipe} from './limit.pipe';

describe('LimitPipe', () => {
  it('create an instance', () => {
    const pipe = new LimitPipe();
    expect(pipe).toBeTruthy();
  });

  it('should not change the input if its less than the limit amount', () => {
    const pipe = new LimitPipe();
    const text = "This text is less the 100 characters";

    const result = pipe.transform(text, 100);

    expect(result).toEqual(text);
    expect(result.length).toEqual(text.length);
  });

  it('should limit the text with ... if the length is longer the limit amount', () => {
    const pipe = new LimitPipe();
    const text = "This text is less the 100 characters";
    const limit = 10

    const result = pipe.transform(text, limit);

    expect(result).not.toEqual(text);
    expect(result.length).toEqual(limit + 3);
    expect(result).toContain('...')
  });
});
