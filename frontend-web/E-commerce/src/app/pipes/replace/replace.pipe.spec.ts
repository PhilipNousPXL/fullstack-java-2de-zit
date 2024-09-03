import {ReplacePipe} from './replace.pipe';

describe('ReplacePipe', () => {
  it('create an instance', () => {
    const pipe = new ReplacePipe();
    expect(pipe).toBeTruthy();
  });

  it('should replace all text occurrences in the given string', () => {
    const pipe = new ReplacePipe();
    const text = "This is some text";
    const pattern = "some"
    const replacement = "new"

    const result = pipe.transform(text, pattern, replacement);

    expect(result).toEqual("This is new text");
  });

  it('should do nothing if any param is missing', () => {
    const pipe = new ReplacePipe();
    const text = "This is some text";
    const replacement = "new"

    // @ts-ignore
    const result = pipe.transform(text, null, replacement);

    expect(result).toEqual(text);
  });
});
